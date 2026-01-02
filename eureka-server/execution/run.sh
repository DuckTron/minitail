#!/bin/sh

GREEN="\033[1;32m"
RED="\033[1;31m"
NC="\033[0m"

echo "[script/eureka] ::::: START"

echo "[build/eureka] ::::: START"
if docker build -t eureka-server .; then
    echo "[build/eureka] ::::: ${GREEN}SUCCESS${NC}"
else
    echo "[build/eureka] ::::: ${RED}FAIL${NC}"
    exit 1
fi

echo "[run/eureka] ::::: START"
echo "[script/eureka] ::::: END"

docker run --rm --name eureka-server eureka-server
