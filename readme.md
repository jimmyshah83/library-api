# Library API Exercise

## Intro

The following assignment will test your knowledge of API design, as well as the MVC design pattern. 
We have taken the liberty of modeling the domain objects, as well as the service and repository layers of the application.
Starting the application will also launch an in-memory H2 database, loaded with a sample data set.

Please read the instructions carefully and completely before beginning the exercise.

## Background

You have been tasked to complete the library service to allow `CardHolder`'s to rent books from the library. 
Every `Book` can be assumed to be unique and the collection of books represents all books in the library. 
A `Rental` record is created when a book is available and is being requested by a `CardHolder` to be on loan. 

## Instructions
Write APIs to perform the following:

    - Rent a book to a card holder.
    - Get all books that are currently on loan.
    - Get all overdue books that are currently on loan.
    - Return a book.

- It is strongly recommended that you read through and familiarize yourself with the entire data model before starting. 
- You may choose to use the existing or create new controllers. 

We encourage you to show off your skills. Examples of ways you can do this are:

    - Write additional APIs you think might be useful.
    - Add testing
Be creative!

Please include any additional notes in `notes.md` including assumptions made, 
design decisions, additional libraries added or anything that you did that was beyond the asks.

## Submission
Please submit a single [patch file](https://git-scm.com/docs/git-format-patch) of your changes.

#### Documentation
For more information about Spring Boot and the related dependencies, please consult the `HELP.md` file.
