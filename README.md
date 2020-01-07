# Backend Interview Problems

# REPORT

# json-manipulation

```
####Task1
All this h...
It was a c...
I knew wha...

####Task2
All this h...
It was a c...
I knew wha...
I did not ...
I had of l...
I walked o...

####Task3
(a) : 11
(afternoon) : 1
(all) : 3
(an) : 3
(and) : 18
(appreciate) : 1

```

Task 4 has printout in the ```main/resources``` folder

# mystery-duplicates

if using an object as key, it directly uses the address it has, so each address is different, that's the culprit for the duplicates

To make object as key and perform correctly, override the hashCode and equals method in the Employee class

by adding this to Employee class can make it correct
```
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  public boolean equals(Object o) {
    return o instanceof Employee && this.hashCode() == o.hashCode() ? true : false;
  }

  public String toString() {
    return firstName + middleInitial + lastName + socialSecurityNumber;
  }
```


# Description

This directory contains two sets of problems in two subdirectores called
`json-manipulation` and `mystery-duplicates`. Each contains its own `README.md`
file which explain a set of `Task`s you need to complete.

Fork this repo and complete all of the `Task`s described in the subproject
`README.md` files, so we can see your solutions and git usage.

## Running the sub-projects

We recommend you run these using IntelliJ. Open this directory in it and it
should automatically have both sub-projects in the Run Configurations list in
the dropdown on the top right of the project window, by the build and run icons.

This is how we will test your solutions!

#### Via the command line:

Assuming you've installed JDK12 and Gradle 6+, you can invoke Gradle directly by
doing `gradle :mystery-duplicates:run` or `gradle :json-manipuation:run` on the
command line.

#### Please Note:

This repo is configured to use IntelliJ as it's an industry standard but you're
not required to have or use any monolithic IDE. Gradle, which is a free,
multiplatform command line build tool, will run everything just fine as
described in previous paragraphs. Whatever you're comfortable with, so long as
the code works: nothing presented here is meant as a "trick."

If you choose not to use an IDE, you'll need java12 installed _as well as_
gradle 6 command line tool. On OS X you can do that by running
`brew install gradle`. Just remember that you can download intellij community
edition for free from https://www.jetbrains.com/idea/download/

Further, one of the reasons we ask you to schedule a block of time with us is so
that we can be available to answer questions! If something seems hard and
doesn't feel like it should be, don't hesitate to reach out (though we might
take five or ten minutes to reply.)

## Hints

- Check `ai.brace.EmployeeProcessor` in the mystery-duplicates project for an
example of how to load the lines of a text file from the `resources` directory.
- Google's GSON library is already included as a dependency in the root
`build.gradle` file's description of the `json-manipulation` sub-project. It
should automatically resolve when imported properly in that project.
