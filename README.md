# JSF2.2-Neo4j-JPA-analyze-inbox
Analyze inbox with neo4j.

Reading inbox from mysql table and generate email and user nodes in neo4j, creating relations. In the working two page
user can input email and watch sent mails count for each TO or CC user, also whole inbox relations, like

user1@gmail.com sent to user5@gmail.com 5 times.

Backend configuration: Spring Data Neo4j, JPA/Mysql.
Front end : JSF2.2 , Primefaces 5.1, Twitter bootstrap.

Mysql database dumpincluded in project, also see persistance unit ans neo4j db path in project.

Just anjoy.

