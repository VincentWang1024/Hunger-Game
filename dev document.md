# Tutorial

**building microservices with Spring Boot & Netflix OSS**.

https://www.pentalog.com/blog/it-development-technology/microservices-spring-boot-netflix-oss/

mongoDB atlas CRUD demo project

https://gitlab.com/v1ncent.wang1024/mongodb-demo

# Git Workflow

## Step 1: Obtain A Task

Find an unassigned issue [here](https://gitlab.com/comp30220/2022/hunger-game/-/issues), and then allocate it to yourself.

## Step 2: Coding

First, fetch updated code: `git fetch origin`

Second, create a feature branch based on the current `main` branch:

```
git checkout main
git pull
git checkout -b issueXX
```

Now you can start to code. After you finished your coding, commit your code in your local machine:

```
git add FILE_NAME
git commit -m "add your commit details"
```

Then you can update your code to the newest again because during your coding, somebody else might submit some other features:

```
git fetch origin
git checkout main
git pull
git merge issueXX ##issueXX is the issue your created before
```

Alternatively, you'd better use `git rebase` to update your code, because this can remove redundant merge commits in your local machine. However, if you don't understand `git rebase`, then using `git merge` would also be acceptable. The commands are for reference if you want to use `git rebase`:

```
git pull --rebase
```

## Step 3: Submit Your Code to Gitlab

After you merged the updated code, now you can submit your code to github to share your code with others:

```
git push origin issueXX
```

## Step 4: Let Others Review Your Code

Review does matter in teamwork. First, others can point out some bugs you didn't notice. Second, you can improve by other's comment.

No code, just refer to [introduction to git](https://product.hubspot.com/blog/git-and-github-tutorial-for-beginners) - Step 8: Create a pull request (PR) for details.

## Step 5: Merge to The Main Branch

The guy who reviewed your code should merge your code to the main branch.