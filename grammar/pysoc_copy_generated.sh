#!/usr/bin/env bash
if [ $0 != "./pysoc_copy_generated.sh" ]; then
    echo "ERROR"
    echo "Run pysoc_copy_generated.sh only in grammar/"
    exit;
fi
echo "Copying antlr output files..."
cp output/PySocialite* ../src/pysocialite/antlr/
echo "Done"
