mkdir /home/postgres
touch /home/postgres/dump-@@@APP@@@.sh
chmod +x /home/postgres/dump-@@@APP@@@.sh
chown postgres.postgres /home/postgres -R
chmod og-rwx /home/postgres

mkdir /home/dev/db
mkdir /home/dev/db/dump
mkdir /home/dev/db/dump/@@@APP@@@
chown dev.postgres /home/dev/db/dump/@@@APP@@@
chmod ug+rwx /home/dev/db/dump/@@@APP@@@
chmod o-rwx /home/dev/db/dump/@@@APP@@@