
#define FILENAME "file_pessoas"

typedef struct Person{
  char nome[200];
  int idade;
} Person;

void new_person(char* name, int age);
void person_change_age(char* name, int age);
void person_change_agev2(int pos, int age);
