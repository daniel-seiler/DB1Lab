#!/bin/sh

if [[ -z $1 ]]; then
	PGPASSWORD=XfgZfBTstd psql -h datenbanken1.ddns.net -p 3690 -U g20
else
	PGPASSWORD=XfgZfBTstd psql -h datenbanken1.ddns.net -p 3690 -U g20 -f $1
fi
