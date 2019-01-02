#FROM cypress/base:10
FROM cypress/browsers:chrome69

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image 
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1

ARG GIT_BRANCH=master

RUN apt-get update && apt-get -y upgrade && apt-get -y autoremove

# shallow-clone the $GIT_BRANCH branch into the e2e folder
RUN git clone -b $GIT_BRANCH --depth 1 https://github.com/metasfresh/metasfresh-webui-frontend.git e2e

WORKDIR /e2e

RUN npm install --save-dev cypress@3.1.4
RUN npm install --save-dev @cypress/snapshot@2.0.1
RUN npm install --save-dev @cypress/webpack-preprocessor@4.0.2

# the following npm install is needed; without it, running cypress will fail like this:
# Your pluginsFile is set to '/e2e/cypress/plugins/index.js', but either the file is missing, it contains a syntax error, or threw an error when required. The pluginsFile must be a .js or .coffee file.

# Please fix this, or set 'pluginsFile' to 'false' if a plugins file is not necessary for your project.

# The following error was thrown:

# Error: Cannot find module 'webpack'
#     at Function.Module._resolveFilename (module.js:485:15)
RUN npm install

RUN $(npm bin)/cypress verify

#add entry-script
COPY run_cypress.sh /
# owner may read and execute
RUN chmod 0500 /run_cypress.sh

ENTRYPOINT ["/run_cypress.sh"]
