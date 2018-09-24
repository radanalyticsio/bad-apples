import psycopg2
from os import environ


class DatabaseLoader:
    # main function
    def __init__(self):
        self.server = environ.get("SERVER")
        self.user = environ.get("USER")
        self.password = environ.get("PASSWORD")
        self.dbname = environ.get("DBNAME")

    # takes the csv and inserts it into the db
    def setup_db(self):
        conn = psycopg2.connect(host=self.server,
                                port=5432,
                                dbname=self.dbname,
                                user=self.user,
                                password=self.password)

        cur = conn.cursor()

        # does table exist
        tb_exists = "select exists(" \
                    "select relname from pg_class where relname='"\
                    + "transactions" + "')"
        cur.execute(tb_exists)
        if cur.fetchone()[0] is False:
            # make table
            cur.execute(
                'create table transactions('
                'Time VARCHAR, '
                'V1 VARCHAR, '
		        'V2 VARCHAR, '
                'V3 VARCHAR, '
                'V4 VARCHAR, '
                'V5 VARCHAR, '
                'V6 VARCHAR, '
                'V7 VARCHAR, '
                'V8 VARCHAR, '
                'V9 VARCHAR, '
                'V10 VARCHAR, '
                'V11 VARCHAR, '
                'V12 VARCHAR, '
                'V13 VARCHAR, '
                'V14 VARCHAR, '
                'V15 VARCHAR, '
                'V16 VARCHAR, '
                'V17 VARCHAR, '
                'V18 VARCHAR, '
                'V19 VARCHAR, '
                'V20 VARCHAR, '
                'V21 VARCHAR, '
                'V22 VARCHAR, '
                'V23 VARCHAR, '
                'V24 VARCHAR, '
                'V25 VARCHAR, '
                'V26 VARCHAR, '
                'V27 VARCHAR, '
                'V28 VARCHAR, '
                'Amount VARCHAR, '
                'Class VARCHAR);')
            conn.commit()
        # copy csv
        f = open(r'data.csv', 'r')
        cur.copy_from(f, "transactions", sep=',')
        conn.commit()
        f.close()

if __name__ == '__main__':
    dbl = DatabaseLoader()
    dbl.setup_db()
