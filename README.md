# Mikrostoritev: Shramba

Ta mikrostoritev predstavlja shrambo/inventar kluba. Omogoča upravljanje z artikli v shrambi in dodajanje izposoj.

## Zagon

Pred zagonom moramo poskrbeti, da teče PostreSQL baza z že narejenimi tabelami.

Nato v root-u projekta poženemo:
`mvnw spring-boot:run` (na Linux/Mac OS `./mvnw` namesto `mvnw`)¸

Port mikrostoritve, kjer bo MS tekla in konfiguracijo za povezavo in delo z bazo podatkov lahko najdemo v `/auth/src/main/resources/application.properties`