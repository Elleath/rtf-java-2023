# Итоговый проект по курсу "Java. Основы программирования". 
#                 Вариант №2 "Школы"

Описание последовательности работы:
1. Создание проекта и подключение необходимых библиотек (OpenCSV, SQLite, lombok, jfreechart)
2. Создание класса Main для основной логики программы
3. Создание класса Parser для парсинга CSV-файла
4. Создание класса School для получения набора объектов из данных, собранных из CSV-файла
5. Создание класса Database для работы с SQLite. Загрузка данных из объектов School в БД
6. Создание запросов к БД для получения данных, необходимых для задач №1-3
7. Создание класса Chart для визуализации данных для задачи №1
8. Разработка основной логики программы, обработка полученных данных

Результат работы программы:
1. После запуска пользователь имеет возможность выбрать действие из списка:
<p align="left">
  <img src="https://github.com/Elleath/rtf-java-2023/assets/122267570/ed17f397-54e8-4636-9928-b280ffdf7aa7">
</p>
   
2. Задача №1. Постройте график по среднему количеству студентов, в 10 различных странах, взять на свой выбор  

Comment: в таблице нет стран, county - округ  
Среднее подсчитано как (общее количество учеников во всех школах округа)/(количество школ в округе)    

Результат решения первой задачи:
<p align="left">
  <img src="https://github.com/Elleath/rtf-java-2023/assets/122267570/fcc0f03d-1db9-46b2-96d9-cbe524b6a5e2">
</p>

<p align="left">
  <img src="https://github.com/Elleath/rtf-java-2023/assets/122267570/3a1e1535-4e21-427f-a714-d628104c4ca6">
</p>

3. Задача №2. Выведите в консоль среднее количество расходов(expenditure) в Fresno, Contra Costa, El Dorado и Glenn, у которых расход больше 10.  

Comment: вероятно, в условии задания допущена ошибка, так как во всех школах расход
в разы больше 10 (составляет 4000-5000 и более), поэтому подсчитано среднее количество расходов
в школах с ДОХОДОМ (income) больше 10  
Среднее подсчитано как (общее количество расходов в школах округа)/(количество школ в округе)  

Результат решения второй задачи:
<p align="left">
  <img src="https://github.com/Elleath/rtf-java-2023/assets/122267570/5c95c4bc-28fd-4f4d-a68c-f570abde7abd">
</p>

4. Задача №3. Выведите в консоль учебное заведение, с количеством студентов равному от 5000 до 7500 и с 10000 до 11000, с самым высоким показателем по математике (math)

Результат решения третьей задачи:
<p align="left">
  <img src="https://github.com/Elleath/rtf-java-2023/assets/122267570/609a3715-2574-46ab-be13-02cdb099b3a4">
</p>

5. Завершение работы программы:
<p align="left">
  <img src="https://github.com/Elleath/rtf-java-2023/assets/122267570/d72d1abe-ab45-4c61-a6ae-2f8de60a44db">
</p>





