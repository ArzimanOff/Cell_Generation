## Основной код проекта находится в папке <a href="https://github.com/ArzimanOff/Cell_Generation/tree/main/app/src/main">main (здесь)</a> 

# Это приложение созданное в качестве тестового задания от <a href="https://hands.ru/">Сервиса Руки</a>

### Условие задания:</br>
Создавая новый мир, он наполняет его клетками. Каждый раз, нажимая на кнопку в приложении, в список клеток добавляется новая:  </br>
— равновероятно она может быть как живой, так и мёртвой; </br>
— жизнь зарождается, если до этого трижды подряд создалась живая клетка;  </br>
— если трижды подряд родилась мёртвая клетка, жизнь рядом умирает.   </br>
Изначально список клеток пуст.   </br>

<h3>Функционал:</h3>

| | |
|--------|--------|
|Название приложения| Cell_Generation|
|Иконка приложения: |<img src="https://github.com/user-attachments/assets/2e03541f-8e36-47e2-8a18-b4431bf27998" alt="drawing" width="100"/>|
| <h3>Главный экран</h3> </br> На главном экране есть кнопка для генерации клеток. В верху экрана отображается статистика созданных клеток, а так же живых и мертвых элементов "Жизнь"|<video src='https://github.com/user-attachments/assets/a70a47f2-8630-4d96-8cbd-8d3873aef511' width="50"/>|
| После начала генерации клеток (если список не пуст) появляется кнопка для очистки списка  (удаление с подтверждением)|<video src='https://github.com/user-attachments/assets/97971a31-b223-4c15-9464-7b1481a3d1fe' width="50"/>|
| Так выглядят живая и мертва клетка соответственно|<img src="https://github.com/user-attachments/assets/b7aa8622-917a-4e4e-879d-1c56a9c1c94d" alt="drawing" width="300"/>|
| При генерации 3 живых клеток подряд создается "Жизнь", </br> а все учавствовашие клетки помечаются зеленым фоном|<img src="https://github.com/user-attachments/assets/573bfc84-7a37-4836-be3c-3742568ad3d9" alt="drawing" width="300"/>|
| При генерации 3 мертвых клеток подряд ранее созданная "Жизнь" погибает (если "Жизнь" была создана) </br> мертвые клетки из-за которых погибла жизнь,</br> а так же сама погибшая жизнь помечается визуально|<img src="https://github.com/user-attachments/assets/fbe307e3-be8b-47f9-b6ac-6eb42ec81b83" alt="drawing" width="200"/> <img src="https://github.com/user-attachments/assets/cd936e1f-1fe5-4e35-8cc8-fa51668f7780" alt="drawing" width="200"/>|
| Так же после генерации 3 мертвых клеток, по ним можно нажать, и тогда они плавно проскроллят список к убитому элементу "Жизнь"|<video src='https://github.com/user-attachments/assets/f6896cdc-7b43-4f24-97c2-672d49c84d7a' width="50"/>|
| Есть поддержка горизонтальной ориентации приложения,</br> прогресс генерации сохраняется при перевороте, а так же при перезаписке приложения|<img src="https://github.com/user-attachments/assets/e1757d63-48e5-4001-b8cc-a161eda998a6" alt="drawing" width="400"/> </br> <img src="https://github.com/user-attachments/assets/ab26e674-42fe-48db-8ec9-20a7f7537b18" alt="drawing" width="400"/>|
| Так же приложение адаптировано под темную тему системы |<img src="https://github.com/user-attachments/assets/791f37b1-a4da-4a26-bd58-5e37ab72a380" alt="drawing" width="200"/> <img src="https://github.com/user-attachments/assets/a85fad6f-af63-4405-8a76-cc44bd989be8" alt="drawing" width="200"/>|
| Полная демонстрация функционала приложения |<video src='https://github.com/user-attachments/assets/7464c505-ea92-42e9-b5d1-7d13a44a922e' width="50"/>|

