Reducere de la problema K-CLIQUE la SAT

Implementare in fisierul KCliqueToSat.java

Tema 2 implementeaza un algoritm de reducere de la problema K-CLIQUE la SAT. Programul este scris in limbajul Java, de
aceea, a fost necesar realizarea unui script care sa ruleze programul compilat prin "./main", astfel incat checker-ul
sa poata rula programul.

Programul consta in implementarea unei clase KCliqueToSat, care contine ca atribute n = numarul de noduri din graf,
k = dimensiunea clicii, matrix = matricea de adiacenta a grafului si nrClauses = numarul total de clauze rezultate.
Programul primeste ca argumente numele fisierului de intrare si numele fisierului de output.
Clasa contine metodele:

-readInput -> metoda care citeste din fisierul de input. Se citeste prima linie, se initializeaza n cu prima valoare din
linie, adica numarul de noduri si apoi k, cu a doua valoare. Intrucat in Java indexul elementelor unei matrici incepe de
la 0, iar pentru o matrice de adiacenta, indexul nodurilor incepe de la 1, am initializat matricea cu n+1 linii si n+1
coloane, astfel incat sa pot accesa elementele matricei de adiacenta cu indexul nodului. Apoi se citesc celelalte n - 1
linii si se initializeaza matricea de adiacenta. Numarul liniei reprezinta nodul, iar elementele de pe linie reprezinta
elementele cu care nodul respectiv este adiacent. In matricea de adiecenta se va pune "1" atat pe pe coloana
corespunzatoare nodului adiacent, de pe linia nodului curent cat si pe coloana nodului curent, de pe linia nodului
adiacent.

constructFirstClauses -> metoda construieste primele clauze din problema, clauze care ne ajuta sa demonstram faptul ca
"exista un al i-lea nod in clica", clauzele fiind de forma: V(xiv), pentru v din V, pentru 1 <= i <= k. Functia
construieste k clauze a cate n variabile, fiecare variabila reprezentand un nod din graf. Fiecare literal din clauza
are formula xiv = (i -1) * n  + v, ce reprezinta un cod unic care indica faptul ca al i-lea nod din clica este nodul
v din graf.

constructSecondClauses -> metoda construieste clauzele care ne ajuta sa demonstram faptul ca "elementele clicii sunt
unice". Pentru fiecare i, j(1 <= i < j <= k) se construiesc clauze de forma: (-xiv)V(-xjv). Nodurile din clica sunt
verificate 2 cate 2, astfel nodul nodul v din clica i nu poate fi si nodul v din clica j.

writeAllSecondVar -> functie auxiliara care ne ajuta la scrierea clauzelor de forma (-xiv)V(-xjv), functia scriind
literalul al doilea din clauza, adica (-xjv).

constructThirdClauses -> metoda construieste clauzele care ne ajuta sa demonstram faptul ca "pentru oricare 2 noduri
intre care nu avvem muchie, cel putin unul dintre ele nu face parte din clica". Pentru fiecare i, j(1 <= i < j <= k)
si pentru oricare u, v(1 <= u < v <= n, unde u si v nu au muchie intre ele) se construiesc clauze de forma: (-xiv)V(-xjv).
Pentru a verifica faptul ca intre 2 noduri nu exista muchie, se verifica daca in matricea de adiacenta, pe linia nodului
u si pe coloana nodului v, nu exista valoarea 1, adica nu exista muchie intre nodurile u si v. Primul for itereaza prin
toate nodurile posibile ale primului nod din perechea(v, u), iar al doilea for itereaza prin toate nodurile posibile ale
celui de-al doilea nod din pereche, intre care nu exista muchie. Mai apoi prin apelul functiilor writeClauses si
writeAllSecondVar se cauta toate combinatiile posibile de cate 2 noduri din clica care nu au muchie intre ele, astfel
incat cel putin unul dintre ele sa nu faca parte din clica.

Toate clauzele sunt retinute intr-un String, si sunt numarate de cate ori se adauga o clauza noua in String. La final,
se construieste un nou string care contine pe prima linie "p cnf" +  numarul de variabile care este egal cu (numarul de
noduri * k) +  numarul de clauze si apoi, se adauga in string, pe urmatoarele linii, clauzele scrise anterior.

writeClauses -> functie auxiliara care ne ajuta la scrierea clauzelor in fisierul de output. Functia primeste ca
parametri 2 String-uri, unul este fisierul de utput, iar celalalt este String-ul final, pe care il scriem in fisier.
