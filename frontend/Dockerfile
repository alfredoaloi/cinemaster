# syntax=docker/dockerfile:1

FROM node:lts
RUN mkdir /home/node/app && chown node:node /home/node/app
RUN mkdir /home/node/app/node_modules && chown node:node /home/node/app/node_modules
WORKDIR  /home/node/app
USER node
COPY --chown=node:node package.json package-lock.json ./
RUN npm ci --quiet
EXPOSE 4200
COPY --chown=node:node . .
CMD ["ng", "serve", "--host=0.0.0.0"]