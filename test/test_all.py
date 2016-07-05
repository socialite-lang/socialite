import unittest

import test_functions
import test_tables
import test_joins

alltests = [test_functions, test_tables, test_joins]

def main():
    for t in alltests:
        t.main()

if __name__ == '__main__':
    unittest.main()
