MySQL initializing in docker:
	docker run -d -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=walletDB -p 3306:3306 --name mysql_win mysql:latest
  
Run -docker exec- on a running container:
  docker exec -it mysql_win bash
  
Connecting to the MySQL Server:
  mysql -u root -p
  
