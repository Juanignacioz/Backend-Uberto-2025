FROM node:19

# We have to install nodemon globally before moving into the working directory
RUN npm install -g nodemon

# Create app directory
#WORKDIR /usr/src/app

# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
#COPY datosInicialesNode/package*.json ./
#COPY datosInicialesNode/cargaDatosInicial.js ./

#RUN npm install

# Bundle app source
COPY . .

EXPOSE 8080

