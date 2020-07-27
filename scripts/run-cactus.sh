#!/usr/bin/env bash

healthcheck() {
    until [[ $(curl -s -o /dev/null -w %{http_code} $1) =~ 200|401|403 ]] ; do
        echo "healthcheck: $1 attempt $attempt_counter"
        if [ ${attempt_counter} -eq ${max_attempts} ];then
        echo "Max attempts reached"
        exit 1
        fi
        attempt_counter=$(($attempt_counter+1))
        sleep $retry_wait
    done
    echo "$1 ready"
}

echo "  _____    _                   _     _                                                     _                             ";
echo " |  __ \  | |                 | |   (_)                                                   | |                            ";
echo " | |__) | | |   __ _   _ __   | |_   _   _ __     __ _      __ _      ___    __ _    ___  | |_   _   _   ___             ";
echo " |  ___/  | |  / _\` | | '_ \  | __| | | | '_ \   / _\` |    / _\` |    / __|  / _\` |  / __| | __| | | | | / __|            ";
echo " | |      | | | (_| | | | | | | |_  | | | | | | | (_| |   | (_| |   | (__  | (_| | | (__  | |_  | |_| | \__ \  _   _   _ ";
echo " |_|      |_|  \__,_| |_| |_|  \__| |_| |_| |_|  \__, |    \__,_|    \___|  \__,_|  \___|  \__|  \__,_| |___/ (_) (_) (_)";
echo "                                                  __/ |                                                                  ";
echo "                                                 |___/                                                                   ";
echo "

#%:.
 #%=   ###%=:
##%=   |##%::
##%=   ###%=:
 #%%=  |##%=:
 ##%== ###%=:    ===
  ##%%=!##%=:    ====
   ###%%##%=   :====
    ######%=: .:====
      ####%%=======
       ###%%%===:
       |%#%%=:=:
       ####%=:
       |##%%:
-------####%:---=
       |%#%%:

"

docker-compose pull -q
docker-compose up -d

attempt_counter=1
retry_wait=10
max_attempts=50 #500 seconds - github runners are slow.

echo "waiting for cdss..."
healthcheck "http://localhost:8080/fhir/metadata"
echo "waiting for fhir server..."
healthcheck "http://localhost:8084/fhir/metadata"
echo "waiting for ems..."
healthcheck "http://localhost:8083/fhir/metadata"
echo "waiting for dos..."
healthcheck "http://localhost:8085/fhir/metadata"
echo "waiting for elasticsearch..."
healthcheck "http://localhost:9200/_cluster/health"

scriptFile="$(dirname "$0")"/supplier-setup.sql
echo "running $scriptFile"
script=$(<$scriptFile)
docker-compose exec -T ems-mysql bash -c "mysql -p\${MYSQL_ROOT_PASSWORD} \${MYSQL_DATABASE} -e \"$script\""