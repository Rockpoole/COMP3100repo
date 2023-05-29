echo "Removing all class files"
rm -rf *.class


echo "Running Client"
javac MyClient.java

python3 ./s2_demo.py "java MyClient" -n -r results/ref_results.json
