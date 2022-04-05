FROM buildkite/agent:3-ubuntu

RUN apt-get update; \
    apt-get install openjdk-8-jdk maven -y

COPY hooks /buildkite/hooks/