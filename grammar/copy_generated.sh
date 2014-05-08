#!/usr/bin/env bash
if [ $0 != "./copy_generated.sh" ]; then
    echo "ERROR"
    echo "Run copy_generated.sh only in grammar/"
    exit;
fi
echo "Copying antlr output files..."
cp output/SociaLite* ../src/socialite/parser/antlr/
echo "Done"
