# AWAP Movie Application

<p>
  
<img alt="Nodejs Badge" longdesc="Nodejs Badge" src="https://img.shields.io/badge/Node%20js-3c873a?style=for-the-badge&logo=nodedotjs&logoColor=white" />

<img alt="PostgreSQL Badge" longdesc="PostgreSQL Badge" src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" />

<img alt="React Badge" longdesc="React Badge" src="https://img.shields.io/badge/React-303030?style=for-the-badge&logo=react&logoColor=61DAFB" />

<img alt="Bootstrap Badge" longdesc="Bootstrap Badge" src="https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white" />

<img alt="Javascript Badge" longdesc="Javascript Badge" src="https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E" />

<img alt="Microsoft Azure Badge" longdesc="microsoft azure Badge" src="https://img.shields.io/badge/microsoft%20azure-008ad7?style=for-the-badge&logo=microsoft-azure&logoColor=white" />

</p>

## :books: Table of the content

- [Introduction](#bulb-introduction)
- [Start](#rocket-start)
- [UI Diagram](#art-ui-diagram)
- [Database Structure](#floppy_disk-database-structure)
- [API document](#page_with_curl-api-document)
- [Deployment](#gear-deployment)
- [Video Demostration](#video_camera-video-demostration)
- [Contacts](#email-contacts)

## :bulb: Introduction

**AppName** is an application that users can...

In this project, we use

- <img src="https://img.shields.io/badge/Backend-Node.js-3c873a">
- <img src="https://img.shields.io/badge/Database-PostgreSQL-316192">
- <img src="https://img.shields.io/badge/Frontend-React-303030">
- <img src="https://img.shields.io/badge/Style and Layout-Bootstrap-563D7C">
- <img src="https://img.shields.io/badge/CI/CD-GitHub Actions-303030">
- <img src="https://img.shields.io/badge/Deployment-Azure Portal-008ad7">

## :rocket: Start

#### :wrench: Install dependencies and create enviroment variables.

```sh
TBD
```

#### :wrench: Establish database

We use **pgAdmin 4** to establish the database in our project.
Make sure the database parameters are matched with the .env file

![server name](/public/photo/db-1.png)
![server parameters](/public/photo/db-2.png)

#### :wrench: Initialize and create the database

![init db file](/public/photo/init-db-1.png)
![init db](/public/photo/init-db-2.png)

#### :wrench: Run the app for development.

```sh
# run the backend under the server folder
cd server
npm run devStart

# run the frontend under the root folder
npm start
```

#### :wrench: Run the backend test script.

```sh
cd server
npm run test

```

## :art: UI Diagram

Please check our WireFrame on Figma.
[Figma WireFrame](https://www.figma.com/design/fEHWvlf1j29CWxDRgzVSWy/MovieApp?node-id=0-1&node-type=canvas)

## :floppy_disk: Database Structure

Here is our Entity Relationship Diagram for our project

You can also check the ERD file under the /server/db folder.
![API document](/public/photo/ERD_diagram.jpg)

## :video_camera: Video Demostration

[Presentation Video](https://youtu.be/vPJG9ogTeoQ)

## :page_with_curl: API document

The openAPI.json describes the API specification for this project which is located under the **server** folder.
![API document](/public/photo/API-document.png)

## :gear: Deployment

#### :wrench: Create GitHub Secrets

TBD

#### :wrench: Enviroment Variables on Azure

TBD

#### :tada: Visit our app on Azure!

TBD

## :email: Contacts

**Send us your questions or support requests.**

- [Aleksi Loddo](mailto:t3loal00@students.oamk.fi)
- [Abolfazl Khazraei](mailto:t3khab00@students.oamk.fi)
- [Sam Chou](mailto:t3chsa01@students.oamk.fi)
