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
                    + "finance" + "')"
        cur.execute(tb_exists)
        if cur.fetchone()[0] is False:
            # make table
            cur.execute(
                'create table transactions('
                'Time INT, '
                'V1 FLOAT, '
		        'V2 FLOAT, '
                'V3 FLOAT, '
                'V4 FLOAT, '
                'V5 FLOAT, '
                'V6 FLOAT, '
                'V7 FLOAT, '
                'V8 FLOAT, '
                'V9 FLOAT, '
                'V10 FLOAT, '
                'V11 FLOAT, '
                'V12 FLOAT, '
                'V13 FLOAT, '
                'V14 FLOAT, '
                'V15 FLOAT, '
                'V16 FLOAT, '
                'V17 FLOAT, '
                'V18 FLOAT, '
                'V19 FLOAT, '
                'V20 FLOAT, '
                'V21 FLOAT, '
                'V21 FLOAT, '
                'V23 FLOAT, '
                'V24 FLOAT, '
                'V25 FLOAT, '
                'V26 FLOAT, '
                'V27 FLOAT, '
                'V28 FLOAT, '
                'Amount VARCHAR, '
                'Class INT);')
            conn.commit()
        # copy csv
        f = open(r'data.csv', 'r')
        cur.copy_from(f, "transactions", sep=',')
        conn.commit()
        f.close()

if __name__ == '__main__':
    dbl = DatabaseLoader()
    dbl.setup_db()
