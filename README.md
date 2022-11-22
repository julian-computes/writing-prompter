# Writing Prompter

Generates writing prompts from txt files.

## Usage

Place txt files (books or otherwise) in `src/main/resources/data` or a directory of your choosing.

Run `mvn clean install`.

Run `java -jar target/writing-prompter.jar`.

If you did not use `src/main/resources/data` as your data directory, pass your custom directory 
as an argument: `java -jar target/writing-prompter.jar /path/to/your/data`.