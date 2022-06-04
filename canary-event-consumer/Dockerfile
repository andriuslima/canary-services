FROM node:14.18.0

WORKDIR /usr/src/app

COPY src/ .
COPY prisma/ .
COPY package.json .
COPY tsconfig.build.json .
COPY tsconfig.json .

RUN yarn install
RUN yarn run prisma generate
RUN yarn build

ENTRYPOINT [ "yarn", "start:prod" ]