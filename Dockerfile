FROM maven:3.8.5-openjdk-17

WORKDIR /usr/src/producao
COPY . .
RUN chmod +x app-startup.sh
EXPOSE 8080
ENTRYPOINT ["/bin/bash"]