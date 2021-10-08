
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


#define affThread(num, msg) printf("th_%s> %s\n", num, msg)

pthread_mutex_t mutexIndiceCourant;
pthread_cond_t condIndiceCourant;
int indiceCourant=-1;

pthread_t threadHandle[NB_MAX_CLIENTS]; /* Threads pour clients*/
int hSocketConnectee[NB_MAX_CLIENTS]; /* Sockets pour clients*/

void * fctThread(void * param);
char * getThreadIdentity();

//a mettre dans une librairie de fonctions
int verifyLogin(char* id, char* mdp);
int verifyTicket(char* numBillet, int nbAccompagnants);
void saveValises(char* numBillet, int vecValses[], int nbValises);
void viderBuffIn();

void HandlerInt(int);

struct trameCIMP trameClient;
struct trameCIMP trameServeur;


int main()
{

	int hSocketEcoute,	/* Socket d'ecoute pour l'attente */
		hSocketService;

	int i,j,			/* variables d'iteration */
		retRecv;		/* Code de retour dun recv */

	struct hostent * infosHost; /*Infos sur le host : pour gethostbyname */
	struct in_addr adresseIP; /* Adresse Internet au format reseau */
	struct sockaddr_in adresseSocket;
	int tailleSockaddr_in;
	int ret, * retThread;


	/* 1. Initialisations */
	puts("* Thread principal serveur demarre *");
	printf("identite = %d.%u\n", getpid(), pthread_self());
	pthread_mutex_init(&mutexIndiceCourant, NULL);
	pthread_cond_init(&condIndiceCourant, NULL);

	/* Si la socket n'est pas utilisee, le descripteur est a -1 */
	for (i=0; i<NB_MAX_CLIENTS; i++) hSocketConnectee[i] = -1;


	/* 2. Creation de la socket d'ecoute */
	hSocketEcoute = socket(AF_INET,SOCK_STREAM,0);
	if (hSocketEcoute == -1)
	{
		printf("Erreur de creation de la socket %d\n", errno);
		exit(1);
	}
	else printf("Creation de la socket OK\n");
	

	/* 3. Acquisition des informations sur l'ordinateur local */
	if ( (infosHost = gethostbyname("moon"))==0)
	{
		printf("Erreur d'acquisition d'infos sur le host %d\n", errno);
		exit(1);
	}
	else printf("Acquisition infos host OK\n");

	memcpy(&adresseIP, infosHost->h_addr, infosHost->h_length);
	printf("Adresse IP = %s\n",inet_ntoa(adresseIP));


	/* 4. Préparation de la structure sockaddr_in */
	memset(&adresseSocket, 0, sizeof(struct sockaddr_in));
	adresseSocket.sin_family = AF_INET;
	adresseSocket.sin_port = htons(PORT_CHCK);
	memcpy(&adresseSocket.sin_addr, infosHost->h_addr, infosHost->h_length);


	/* 5. Le système prend connaissance de l'adresse et du port de la socket */
	if (bind(hSocketEcoute, (struct sockaddr *)&adresseSocket,
	sizeof(struct sockaddr_in)) == -1)
	{
		printf("Erreur sur le bind de la socket %d\n", errno);
		exit(1);
	}
	else printf("Bind adresse et port socket OK\n");


	/* 6. Lancement des threads */
	for (i=0; i<NB_MAX_CLIENTS; i++)
	{
		ret = pthread_create(&threadHandle[i],NULL,fctThread,NULL);
		printf("Thread secondaire %d lance !\n", i);
		ret = pthread_detach(threadHandle[i]);
	}

	do
	{
		/* 7. Mise a l'ecoute d'une requete de connexion */
		puts("Thread principal : en attente d'une connexion");
		if (listen(hSocketEcoute,SOMAXCONN) == -1)
		{
			printf("Erreur sur le listen de la socket %d\n", errno);
			close(hSocketEcoute); /* Fermeture de la socket */
			exit(1);
		}
		else printf("Listen socket OK\n");


		/* 8. Acceptation d'une connexion */
		tailleSockaddr_in = sizeof(struct sockaddr_in);
		if ( (hSocketService =
		accept(hSocketEcoute, (struct sockaddr *)&adresseSocket, &tailleSockaddr_in) )
		== -1)
		{
			printf("Erreur sur l'accept de la socket %d\n", errno);
			close(hSocketEcoute); /* Fermeture de la socket */
			exit(1);
		}
		else printf("Accept socket OK\n");


		/* 9. Recherche d'une socket connectee libre */
		printf("Recherche d'une socket connecteee libre ...\n");
		for (j=0; j<NB_MAX_CLIENTS && hSocketConnectee[j] !=-1; j++);

		if (j == NB_MAX_CLIENTS)
		{
			printf("Plus de connexion disponible\n");
			sprintf(trameServeur.DATA.message,DOC);

			if (send(hSocketService, &trameServeur, MAXSTRING, 0) == -1)
			{
				printf("Erreur sur le send de refus%d\n", errno);
				close(hSocketService); /* Fermeture de la socket */
				exit(1);
			}
			else printf("Send socket refusee OK");

			close(hSocketService); /* Fermeture de la socket */
		}
		else
		{
			/* Il y a une connexion de libre */
			printf("Connexion sur la socket num. %d\n", j);
			pthread_mutex_lock(&mutexIndiceCourant);
			hSocketConnectee[j] = hSocketService;
			indiceCourant=j;
			pthread_mutex_unlock(&mutexIndiceCourant);
			pthread_cond_signal(&condIndiceCourant);
		}
	}
	while (1);

	/* 10. Fermeture de la socket d'ecoute */
	close(hSocketEcoute); /* Fermeture de la socket */
	printf("Socket serveur fermee\n");

	puts("Fin du thread principal");
	return 0;
}


/* -------------------------------------------------------- */
void * fctThread (void *param)
{
	char * nomCli, *buf = (char*)malloc(100);
	//char msgClient[MAXSTRING], msgServeur[MAXSTRING];
	//int vr = (int)(param);
	int finDialogue=0, i, iCliTraite;
	int temps, retRecv;
	char * numThr = getThreadIdentity();
	int hSocketServ;
	int testLogin, testTicket;
	int requeteRecue;
	int nbAccompagnants;
	int nbVal;
	float poidsTotValises, poidsExcedent;
	int valisesOrNot[20] = {0};
	char * numBilletClient;


	while (1)
	{
		/* 1. Attente d'un client à traiter */
		pthread_mutex_lock(&mutexIndiceCourant);
		while (indiceCourant == -1)
		pthread_cond_wait(&condIndiceCourant, &mutexIndiceCourant);
		iCliTraite = indiceCourant; indiceCourant=-1;
		hSocketServ = hSocketConnectee[iCliTraite];
		pthread_mutex_unlock(&mutexIndiceCourant);
		sprintf(buf,"Je m'occupe du numero %d ...", iCliTraite);affThread(numThr, buf);

		/* 2. Dialogue thread-client */

		int etatServ = 0;
		finDialogue=0;

		do
		{ 

			if ((retRecv=recv(hSocketServ, &trameClient, TAILLE_TRAME,0)) == -1)
			{
				printf("Erreur sur le recv de la socket connectee : %d\n", errno);
				close (hSocketServ); exit(1);
			}
			else
			{
				requeteRecue = trameClient.requete;
				
				switch(requeteRecue)
				{
					case LOGIN_OFFICER:

						printf("Requete recue : LOGIN_OFFICER\n");
						printf("LOGIN AGENT : %s\n",trameClient.DATA.login.identifiant);
						printf("MDP AGENT : %s\n",trameClient.DATA.login.mdp);

						//verifyLogin vérifie si l'agent a un identifiant/mdp valide depuis fichier
						testLogin = verifyLogin(trameClient.DATA.login.identifiant,
												trameClient.DATA.login.mdp);


						if(testLogin == 0)
						{

							trameServeur.requete = FAIL_LOGIN;
							sprintf(trameServeur.DATA.message,
									"Identifiant ou mot de passe incorrect, connexion refusée");

							if (send(hSocketServ,&trameServeur, TAILLE_TRAME, 0) == -1)
							{
								printf("Erreur sur le send de la socket %d\n", errno);
								close(hSocketServ); // Fermeture de la socket
								exit(1);
							}
							else printf("Send socket OK\n");

							finDialogue = 1;
						}
						else
						{
							etatServ = 1; //le login est OK, le serv peut recv des autres requetes

							sprintf(trameServeur.DATA.message,"Connexion acceptee !");

							if (send(hSocketServ,&trameServeur, TAILLE_TRAME, 0) == -1)
							{
								printf("Erreur sur le send de la socket %d\n", errno);
								close(hSocketServ); // Fermeture de la socket
								exit(1);
							}
							else printf("Send socket OK\n");
						}


						break;

					case CHECK_TICKET:

						nbAccompagnants = 0;

						printf("Requete recue : CHECK_TICKET\n");
						printf("Vérification du billet %s possédant %d accompagnants : \n",trameClient.DATA.billet.numBillet,trameClient.DATA.billet.nbAccompagnants);

						numBilletClient = (char*) malloc(sizeof(trameClient.DATA.billet.numBillet));
						strcpy(numBilletClient,trameClient.DATA.billet.numBillet);

						testTicket = verifyTicket(trameClient.DATA.billet.numBillet,trameClient.DATA.billet.nbAccompagnants);

						if(testTicket == 0)
						{
							trameServeur.requete = FAIL_TICKET;
							sprintf(trameServeur.DATA.message,
									"Billet ou nombre d'accompagnants incorret, check in impossible !");

							if (send(hSocketServ,&trameServeur, TAILLE_TRAME, 0) == -1)
							{
								printf("Erreur sur le send de la socket %d\n", errno);
								close(hSocketServ); // Fermeture de la socket
								exit(1);
							}
							else printf("Send socket OK\n");

							finDialogue = 1;

						}
						else
						{	
							nbAccompagnants = trameClient.DATA.billet.nbAccompagnants;
							sprintf(trameServeur.DATA.message,"Billets et nombre d'accompagnants OK ! Encodez les bagages : \n");

							if (send(hSocketServ,&trameServeur, TAILLE_TRAME, 0) == -1)
							{
								printf("Erreur sur le send de la socket %d\n", errno);
								close(hSocketServ); // Fermeture de la socket
								exit(1);
							}
							else printf("Send socket OK\n");
						}


						break;

					case CHECK_LUGGAGE:

						printf("Requete recue : CHECK_LUGGAGE\n");

						poidsTotValises = 0;
						poidsExcedent = 0;
						nbVal = 0; //nombre de valises que le serveur doit enregistrer


						while(trameClient.requete == CHECK_LUGGAGE)
						{
							poidsTotValises+= trameClient.DATA.valClient.poidsValise;
							

							if(trameClient.DATA.valClient.ifValise == 'O')
							{
								valisesOrNot[nbVal] = 1;
							}
							else
							{
								valisesOrNot[nbVal] = 0;
							}

							nbVal++;

							if ((retRecv=recv(hSocketServ, &trameClient, TAILLE_TRAME,0)) == -1)
							{
								printf("Erreur sur le recv de la socket connectee : %d\n", errno);
								close (hSocketServ); exit(1);
							}

						}


						printf("Poids total bagages : %.2f kg\n",poidsTotValises);

						poidsExcedent = poidsTotValises - (nbVal*20);

						//char ifPaye;
						if(poidsExcedent>0)
						{
							printf("Excedent poids : %.2f kg\n",poidsExcedent);
							printf("Supplément à payer : %.2f €\n",poidsExcedent*3.0);

							sprintf(trameServeur.DATA.message,"Supplément à payer :  %.2f€",&poidsExcedent);

							if (send(hSocketServ,&trameServeur, TAILLE_TRAME, 0) == -1)
							{
								printf("Erreur sur le send de la socket %d\n", errno);
								close(hSocketServ); // Fermeture de la socket
								exit(1);
							}
							else printf("Send socket OK\n");
						}
						else
						{
							sprintf(trameServeur.DATA.message,
								"Enregristements des valises OK, rien à payer !");

							if (send(hSocketServ,&trameServeur, TAILLE_TRAME, 0) == -1)
							{
								printf("Erreur sur le send de la socket %d\n", errno);
								close(hSocketServ); // Fermeture de la socket
								exit(1);
							}
							else printf("Send socket OK\n");
						}

						break;


					case PAYMENT_DONE:

						printf("Requete recue : PAYMENT_DONE\n");

						//printf("Num billet : %s\n",numBilletClient);
						//printf("Nb valises : %d\n",nbVal);

						saveValises(numBilletClient,valisesOrNot,nbVal);
				};
			}
		}
		while (!finDialogue);

		/* 3. Fin de traitement */
		pthread_mutex_lock(&mutexIndiceCourant);
		hSocketConnectee[iCliTraite]=-1;
		pthread_mutex_unlock(&mutexIndiceCourant);

		printf("Fin de connexion au client actuel, attente d'une nouvelle connexion client \n");
	}

	close (hSocketServ);
	return 0;
}

char * getThreadIdentity()
{
	unsigned long numSequence;
	char *buf = (char *)malloc(30);
	numSequence = 0;//pthread_getsequence_np( pthread_self( ) ); test avec 0 OK
	sprintf(buf, "%d.%u", getpid(), numSequence);
	return buf;
}

int verifyLogin(char* id, char* mdp)
{


	FILE* fp = fopen("login.csv","r");
	int trouve = 0;

	if(fp == NULL)
	{
		printf("Impossible d'ouvrir le fichier\n");
	}
	else
	{
		char buffer[512];
		char* iden;
		char* passw;

		while(fgets(buffer,512,fp) && !trouve)
		{
			char* token;

			token = strtok(buffer,";");
			iden = (char*) malloc(sizeof(token));
			strcpy(iden,token);
 
 			if((strcmp(iden,id))==0)
 			{
 				token = strtok(NULL,"; \n");
				passw = (char*) malloc(sizeof(token));
				strcpy(passw,token);

				if((strcmp(passw,mdp))==0)
				{
					printf("Agent %s avec mot de passe %s trouvé dans la liste !\n",iden,passw);
					trouve = 1;
				}
				free(passw);

 			}

 			free(iden);			
		}

		return trouve;
	}

}

int verifyTicket(char* numBillet, int nbAccompagnants)
{
	FILE* fp = fopen("checkticket.csv","r");
	int trouve = 0;

	if(fp == NULL)
	{
		printf("Impossible d'ouvrir le fichier\n");
	}
	else
	{
		char buffer[512];
		char* checkBillet;
		int checkAccomp;

		while(fgets(buffer,512,fp) && !trouve)
		{
			char* token;

			token = strtok(buffer,";");
			checkBillet = (char*) malloc(sizeof(token));
			strcpy(checkBillet,token);

			if((strcmp(checkBillet,numBillet))==0)
			{
				token = strtok(NULL,"; \n");
				checkAccomp = atoi(token);

				if(checkAccomp == nbAccompagnants)
				{
					printf("Billet %s possédant %d accompagnants OK\n",numBillet,nbAccompagnants);
					trouve = 1;
				}
			}

			free(checkBillet);	
		}

		return trouve;
	}

}



void saveValises(char* numBillet, int vecValises[], int nbValises)
{


	char* numFich = (char*) malloc(sizeof(50));

	sprintf(numFich,"362%s_lug.csv",numBillet);

	FILE* fp = fopen(numFich,"w");

	if(fp == NULL)
	{
		printf("Impossible d'ouvrir le fichier\n");
	}
	else
	{
		char buffer[512];

		
		for(int i = 0; i < nbValises;i++)
		{
			//sprintf("")
			fprintf(fp,"362_%s_%d;",numBillet,i);

			if(vecValises[i] == 1)
			{
				fprintf(fp,"VALISE\n");
			}
			else
			{
				fprintf(fp,"NOT VALISE\n");
			}
		}

		
	}

	fclose(fp);

}

void viderBuffIn()		//on récupère tous les caractères du buffer stdin
{
	int c = 0;
    while (c != '\n' && c != EOF)
    {	
        c = getchar();
    }
}

/*
void HandlerInt(int x)
{

	for(int i = 0; i<NB_MAX_CLIENTS;i++)
	{
		if(hSocketConnectee[i] != -1)
		{
			close(hSocketConnectee[i]);
		}
	}

	close(hSocketEcoute);
	close(hSocketService);

	printf("ctrl-c reçu, fermeture du serveur \n");
	exit(1);
}

*/