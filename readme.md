#Инструкция по запуску cmd
1. cd focus_start (открыть папку с проектом)
2. mvn install
3. положить файлы для сортировки в папку проекта
4. java -cp ".;target/Focus_Start-1.0-SNAPSHOT.jar" Main (необходимые аргументы: тип данных, способ сортировки, файлы для сортировки, out-файл). Пример: java -cp ".;target/Focus_Start-1.0-SNAPSHOT.jar" Main -i in1.txt in2.txt out.txt
5. out-файл появится в папке проекта