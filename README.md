# Android-Library-Management-System
This is a library management system based on Android platform. All features implemented for two users, normal user and admin.

General Description/ Feature
1. Two roles implemented in this app, user and admin.
2. Admin can add new books to the library (such as: book id, book name, book author, book category, book status(0:book unavailable, 1:book available), book sector(indicate the sector where this book located) and choose book cover for this book).
3. Admin can view all the books currently available in the library and manage books, edit book information and delete book.
4. Admin can manage user. Admin can view all the users currently in the library system, its id, password and type, also delete users in the system.
5. Admin will handle book return. Admin can use the return book button in main screen to initialize the camera and scan barcode to return this book back to library.
6. User can view all books in the library, click the book item to enter the detail page of this book.
7. User can rate this book by star rating bar and leave comment, also, user can issue this book by himself by clicking borrow button on detail page to initialize phone camera, scan book barcode and issue the book.
8. User can search the book he wants by typing in book id and click search button, the search result will show below.
9. First, under manage account function. User can view all the book he currently borrowed, book name, due date for return and pending current pending fine.
10. Second, under manage account function. User can change its account password by authenticate old password and twice-input of new password. If the old password does not match from database or two new password does not match, password change will fail.

User Guide

Prerequisite
1. When first enter the app, click into admin login page and click “create” below to create new admin account to proceed the following operations. Type in initial id and password, set user type as “admin”. 
2. Second, you need to add books into database first to use and test the function. Book id should be the number of barcode.

User
1. User can login with a given account, with initial id and password, and will required to change password the first time login.
2. In user main screen, there are three buttons, “Book list”, “Search” and “Manage account”. Under “Manage account activity”, there are two functions, “My book” and “Change password”.
3. In book list activity, user can view all the books currently in the library with its id, name, author and category. User can click the book he/she interested in and view the detail information about this book.
4. In the detail screen, user can rate this book using star rating bar and leave a comment below and submit.
5. User can borrow this book by clicking “Borrow” button on the detail page of the book, this’ll initiate the camera on the phone, user will have to scan the book barcode and click “Borrow”. By doing this, user have been issued the book successfully.
6. The book id is the number of barcode, use barcode number to generate barcode online and then use scan function.
7. In the Search function, user can search for a book by its id or name. The search result will show as a listview below, and user can click the search result and enter the screen of detail of the book and do the same operations as described in 4 and  5.
8. First, under manage account function. User can view all the books he/she borrowed, including book name, due date and pending fine of this book.
9. Second, under manage account function. User can change its own password, and will be required to enter old password, if old password is not matched from database or reentered new password does not match the new password, password changing will not success.

Admin
1. Use admin account to login the admin main screen.
2. In admin main screen, there are four buttons, “Add book”, “Manage book”, “Manage user” and “Return book”.
3. Click “Add book” to add new books into bookDB.db database, book table. You need to add book ID(which will be the number of barcode), book name, book author, book category, book status(which marks if the book is available in the library or not, 0 marks unavailable, 1 marks available), book sector(in which location the book is, number from 1-9), and select a picture from gallery as book cover. Click “Submit” to save book information.
4. After adding book into table. In manage book screen, admin can see all books currently in the library, modify and delete basic information, book name, book author and book category.
5. In manage user screen, admin can add and delete user, both type user and admin, into user table. Normal user cannot create account, admin will create user account and assign account to individual user, user will have to change its password the first time he/she login.
6. In return book activity, admin will use camera to scan book barcode and choose “Return” after scan result shows, then the book will be returned.
