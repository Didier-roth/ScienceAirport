#define MAXSTRING 100
#define TAILLE_CHAMP 50
#define NB_MAX_CLIENTS 2 /* Nombre maximum de clients connectes */
#define EOC -1
#define DOC "DENY_OF_CONNEXION"

#define PORT_CHCK 50001 /* Port d'ecoute de la socket serveur */

#define LOGIN_OFFICER 		1
#define LOGOUT_OFFICER		2
#define CHECK_TICKET		3
#define CHECK_LUGGAGE		4
#define CHECK_LUGGAGE_END	5
#define PAYMENT_DONE		6

#define FAIL_LOGIN			31
#define FAIL_TICKET			32
#define FAIL_LUGGAGE		33


struct connexion
{
	char identifiant[TAILLE_CHAMP];
	char mdp[TAILLE_CHAMP];
};

struct billets
{
	char numBillet[TAILLE_CHAMP];
	int nbAccompagnants;
};

struct valise
{
	float poidsValise;
	char ifValise;
};

struct trameCIMP
{
	//char requete[30]; on ne fait plus en chaine de car mais un int représentant la requete

	int requete;
	
	union
	{
		struct connexion login;
		char message[MAXSTRING];
		struct billets billet;
		struct valise valClient;
	}DATA;
};

#define TAILLE_TRAME sizeof(struct trameCIMP)