
#include <stdio.h>
#include <stdlib.h> 
#include <unistd.h> //pour les fonctions telles que close
#include <sys/types.h> //pour les types de socket
#include <sys/socket.h> //pour les noms de protocoles
#include <errno.h>
#include <netinet/in.h> //pour le in_addr
#include <netinet/tcp.h>
#include <netdb.h> //pour le gethostbyname 
#include <arpa/inet.h>
#include <string.h>
#include <time.h>
#include <pthread.h>
#include "CIMP.h"


struct trameCIMP trameClient;
struct trameCIMP trameServeur;

void viderBuffIn();

int main()
{

	int hSocket; /* Handle de la socket */
	struct hostent * infosHost; /*Infos sur le host : pour gethostbyname */
	struct in_addr adresseIP; /* Adresse Internet au format reseau */
	struct sockaddr_in adresseSocket;
	int tailleSockaddr_in;
	int ret; /* valeur de retour */
	int retRecv;
	int cpt=0;

	/* 1. Création de la socket */
	hSocket = socket(AF_INET, SOCK_STREAM, 0);
	if (hSocket == -1)
	{
		printf("Erreur de creation de la socket %d\n", errno);
		exit(1);
	}
	else printf("Creation de la socket OK\n");

	/* 2. Acquisition des informations sur l'ordinateur distant */
	if ( (infosHost = gethostbyname("moon"))==0)
	{
		printf("Erreur d'acquisition d'infos sur le host distant %d\n", errno);
		exit(1);
	}
	else printf("Acquisition infos host distant OK\n");
	memcpy(&adresseIP, infosHost->h_addr, infosHost->h_length);
	printf("Adresse IP = %s\n",inet_ntoa(adresseIP));

	/* 3. Préparation de la structure sockaddr_in */
	memset(&adresseSocket, 0, sizeof(struct sockaddr_in));
	adresseSocket.sin_family = AF_INET; /* Domaine */
	adresseSocket.sin_port = htons(PORT_CHCK);
	memcpy(&adresseSocket.sin_addr, infosHost->h_addr,infosHost->h_length);

	/* 4. Tentative de connexion */
	tailleSockaddr_in = sizeof(struct sockaddr_in);
	if (( ret = connect(hSocket, (struct sockaddr *)&adresseSocket, tailleSockaddr_in) )
	== -1)
	{
		printf("Erreur sur connect de la socket %d\n", errno);
		switch(errno)
		{
			case EBADF : printf("EBADF - hsocket n'existe pas\n");
			break;

			default : printf("Erreur inconnue ?\n");
		}
		close(hSocket);
		exit(1);
	}
	else printf("Connect socket OK\n");

	
	//login
	trameClient.requete = LOGIN_OFFICER;

	printf("\nSaisissez votre identifiant : "); 
	scanf("%s",&trameClient.DATA.login.identifiant);

	printf("\nSaisissez votre mot de passe : "); 
	scanf("%s",&trameClient.DATA.login.mdp);

	if (send(hSocket,&trameClient, TAILLE_TRAME, 0) == -1)
	{
		printf("Erreur sur le send de la socket %d\n", errno);
		close(hSocket); // Fermeture de la socket
		exit(1);
	}
	else printf("Send socket OK\n");

	if ((retRecv=recv(hSocket,&trameServeur, TAILLE_TRAME,0)) == -1)
	{
		printf("Erreur sur le recv de la socket connectee : %d\n", errno);
		close (hSocket); exit(1);
	}

	printf("Message recu : %s\n",trameServeur.DATA.message);

	if(trameServeur.requete == FAIL_LOGIN)
	{
		close (hSocket); 
		exit(1);
	}


	/* 5. Envoi d'un message client */
	do
	{
		//encodages des billets

		printf("\nVOL 362 POWDER-AIRLINES - Peshawar 6h30");

		trameClient.requete = CHECK_TICKET;

		printf("\nNuméro du billet ? : "); 
		scanf("%s",trameClient.DATA.billet.numBillet);

		printf("\nNombre d'accompagnants ? :"); 
		scanf("%d",&trameClient.DATA.billet.nbAccompagnants);
		
		if (send(hSocket, &trameClient, TAILLE_TRAME, 0) == -1)
		{
			printf("Erreur sur le send de la socket %d\n", errno);
			close(hSocket); // Fermeture de la socket
			exit(1);
		}
		else printf("Send socket OK\n");


		if ((retRecv=recv(hSocket,&trameServeur, TAILLE_TRAME,0)) == -1)
		{
			printf("Erreur sur le recv de la socket connectee : %d\n", errno);
			close (hSocket); exit(1);
		}

		printf("Message recu : %s\n",trameServeur.DATA.message);
		if(trameServeur.requete == FAIL_TICKET)
		{
			close (hSocket); 
			exit(1);
		}

		//encodage des valises

		float poids = 0;
		char boolValise = 'N';

		trameClient.requete = CHECK_LUGGAGE;

		for(int i = 0;i<20;i++)
		{
			printf(" \nPoids du bagage n°%d <0 si fini> : ",i);
			viderBuffIn(); 
			scanf("%f",&poids);


			if(poids>0)
			{
				printf("\nValise ou non ? (O ou N) : "); 
				viderBuffIn();
				scanf("%c",&boolValise);

				trameClient.DATA.valClient.poidsValise = poids;
				trameClient.DATA.valClient.ifValise = boolValise;

				if (send(hSocket, &trameClient, TAILLE_TRAME, 0) == -1)
				{
					printf("Erreur sur le send de la socket %d\n", errno);
					close(hSocket); // Fermeture de la socket
					exit(1);
				}
				else printf("Send socket OK\n");
			}
			else
			{
				trameClient.requete = CHECK_LUGGAGE_END; // on signale qu'on a fini d'enregistrer les bagages

				if (send(hSocket, &trameClient, TAILLE_TRAME, 0) == -1) 
				{
					printf("Erreur sur le send de la socket %d\n", errno);
					close(hSocket); // Fermeture de la socket
					exit(1);
				}
				else printf("Send socket OK\n");

				break;			
			}
		}

		if ((retRecv=recv(hSocket,&trameServeur, TAILLE_TRAME,0)) == -1)
		{
			printf("Erreur sur le recv de la socket connectee : %d\n", errno);
			close (hSocket); exit(1);
		}


		printf("Message recu : %s\n",trameServeur.DATA.message);

		trameClient.requete = PAYMENT_DONE;

		if (send(hSocket, &trameClient, TAILLE_TRAME, 0) == -1) 
		{
			printf("Erreur sur le send de la socket %d\n", errno);
			close(hSocket); // Fermeture de la socket
			exit(1);
		}
		else printf("Send socket OK\n");


	}
	while (trameClient.requete != EOC);//&& strcmp(msgServeur, DOC));

	/* 9. Fermeture de la socket */
	close(hSocket); /* Fermeture de la socket */
	printf("Socket client fermee\n");
	printf("%d messages envoyes !", cpt);
	return 0;
}


void viderBuffIn()		//on récupère tous les caractères du buffer stdin
{
	int c = 0;
    while (c != '\n' && c != EOF)
    {
        c = getchar();
    }
}