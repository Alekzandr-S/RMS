# Rabbit Management System

The main goal is make the process of managing livestock as streamlined and as painless as possible. To get started all 
you need is to fill out the short registration form...

        FORMS..
And that's it. You're all set to begin using the system.

##Entering Rabbit Information

Once you're logged in you'll be led to the home page/dashboard which is pretty empty at time your first sign up. We need
data to work with, so the first step is to input your does (females) and bucks (males). Right at the top of the screen 
in the main menu is the Manage Does and Manage Bucks button we are mainly interested in:

      HOME-VIEW...

##Adding a Rabbit

Clicking on Manage Does, we're taken to the list of all our does, which is currently empty. To add our first doe, click 
Add Doe button on top of the grid:

    DOE-VIEW

For each rabbit created, the system mainly needs to know the cage reference identifying the location of the animal. 
Click the save button below the form, and the doe will be saved to the system.
It should be noted however that a doe must be at least 5 months old before it can be old enough to begin to mate. Mating
younger does adversely affect their health, growth and performance in the long run.

        DOE-FORM

From this point on out this Doe will have a unique id in the system. All information you enter from this point on will
follow along with it.

##Other Rabbit Information

Whilst the cage reference is the most required piece of information, the system also allows you to keep of the following
about the animals;
-Breed
-Parent doe and sire
-Date of birth

##Task Tracker

Assuming you've now added all of your does and bucks to the system you can begin to take advantage of one of the best 
features available through the dashboard, the task tracker.
The system will be able to keep track of the following major events:
-Palpation test are due to confirm pregnancy.
-Breeding boxes must be put in cages in preparation for birth.
-Kits are due for weaning from their mothers.

                               USER MANUAL
## Creating animals
Let's start with an example of how to create a doe. The step outlined here are identical for creating a buck and 
weaners.
After opening the Manage Does. Click the add 'Add Doe' button on top of the grid. This will bring up the new doe form:

        DOE-FORM...
Once you've created an animal, you'll now be able to see it in the list of all your does.
        DOE-LIST...
        
##Managing Litters
Keeping track of the progress of a doe' s litters has never been easier or simpler. For each doe you create its litters
may be viewed from the Manage Kits section

        WEANER-KITS...
To record that doe has been mated, simply check the 'Mate' box:

        DOE-FORM...
If you go to the task tracker you'll notice that the date to palpation test has been updated.

        CONFIRMING PREGNANCY
10 days after mating it should be possible to confirm whether a doe is pregnant through 
palpition of it's abdomen. Once you are sure that a doe is pregnant after having mated,
mark the 'Pregnant' check box in the Doe form and save. Once this has been done, you'll see
that the 'Pregnancy' will have changed status to 'Yes'

        REMATING DOE
If you find that after 10 days or more the doe failed to become pregnant, simply update the
mating check box to when the doe was mated.

        DAYS TO BOX(PLACE BOX)
On average, it should take 30 days between successful mating and the birth of a litter. 
However, the 'Place Box' in the task tracker indicate the number of days until a box needs
to be put in for the doe to prepare to give birth.

        RECORD BIRTH
Simply enter the date they were born and initial of kits under the 'Manage Weaners' section
. This process also serve as a creation of kits.

##Managing Sales
As weaners reach maturity you'll also want to record sales made:

      SALES...

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different 
IDEs](https://vaadin.com/docs/latest/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).




