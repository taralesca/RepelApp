# Repel
Social Android application, current version is unstable/under construction. 

## Project details
* [User Stories](https://github.com/valianttech/RepelApp/projects/1)
* [Code documentation](https://github.com/valianttech/RepelApp/wiki)
* [Development status](https://github.com/valianttech/RepelApp/projects/3)

## Development standards

### Build details
To make sure everything works as expected, use:
* Gradle version 5.2+.
* Android Studio 3.3.2+

Before committing, please read through this first: [AOSP Java Code Style](https://source.android.com/setup/contribute/code-style). Poorly formatted code may be dropped automatically later.

### Commit tags:
* [STABLE] commits are tested and agreed upon by every member. Can be pushed to **master**.
* [NIGHTLY] commits are tested are waiting for approval from other members. Can be pushed to **development**.
* [UNTESTED] commits can only be pushed to **experimental**.
* [BUGGED] code can be pushed to **experimental** only if other members agree or specifically ask for it.

Sample commit message in **master** branch:
```
[STABLE] Add user login.
```

