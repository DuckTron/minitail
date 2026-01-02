#!/bin/sh

GREEN="\033[1;32m"
RED="\033[1;31m"
NC="\033[0m"

echo "[script/gateway] ::::: START"

echo "[build/gateway] ::::: START"
if docker build -t api-gateway .; then
    echo "[build/gateway] ::::: ${GREEN}SUCCESS${NC}"
else
    echo "[build/gateway] ::::: ${RED}FAIL${NC}"
    exit 1
fi

echo "[run/gateway] ::::: START"
echo "[script/gateway] ::::: END"

docker run --rm --name api-gateway api-gateway
