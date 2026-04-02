PgAdmin
link: http://localhost:8080
id:root@root.com
password:root

genere un jwtSecret avec nodeJS:
node -e "console.log(require('crypto').randomBytes(64).toString('hex'))"
