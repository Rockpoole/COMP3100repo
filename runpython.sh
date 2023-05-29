echo "Removing all class files"
rm -rf *.class


echo "Running Client"
javac MyClient.java

python3 s2_test.py -n java MyClient -r results/ref_results.json
