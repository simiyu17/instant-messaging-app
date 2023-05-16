###############
### STAGE 1: Build app
###############
FROM node:16.16.0-alpine as builder

RUN apk add --no-cache git

WORKDIR /usr/src/app

ENV PATH /usr/src/app/node_modules/.bin:$PATH

COPY ./ /usr/src/app/

RUN npm cache clear --force &&\

  npm config set fetch-retry-maxtimeout 120000 &&\

  npm install &&\

  npm run build 

###############
### STAGE 2: Serve app with nginx ###
###############
FROM nginx:1.23.4-alpine

COPY --from=builder /usr/src/app/dist/chat-client /usr/share/nginx/html

COPY /nginx.conf  /etc/nginx/conf.d/default.conf

EXPOSE 80
