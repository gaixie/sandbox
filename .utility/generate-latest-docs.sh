#!/bin/bash
#
# DISCRIPTION: 通过 travis-ci 自动生成 javadoc 并发布到 github pages.
# see http://benlimmer.com/2013/12/26/automatically-publish-javadoc-to-gh-pages-with-travis-ci/ for details

if [ "$TRAVIS_REPO_SLUG" == "gaixie/sandbox" ] && \
    [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ] && \
    [ "$TRAVIS_PULL_REQUEST" == "false" ]; then

    echo -e "Publishing javadoc & report...\n"

    cp -R build/docs/javadoc $HOME/javadoc-latest
    cp -R build/reports/tests $HOME/test-latest

    echo -e "dbuser = $DBUSER\n"
    cd $HOME
    git config --global user.email "travis@travis-ci.org"
    git config --global user.name "travis-ci"
    git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/gaixie/sandbox gh-pages > /dev/null

    cd gh-pages

    if [ "$TRAVIS_BRANCH" == "master" ]; then
        git rm -rf ./core-api/latest ./core-test/latest
        cp -Rf $HOME/javadoc-latest ./core-api/latest
        cp -Rf $HOME/test-latest ./core-test/latest
    else
        git rm -rf ./core-api/snapshot ./core-report/snapshot
        mkdir -p ./core-api/snapshot ./core-test/snapshot
        cp -Rf $HOME/javadoc-latest ./core-api/snapshot
        cp -Rf $HOME/test-latest ./core-test/snapshot
    fi

    git add -f .
    git commit -m "Lastest javadoc & report on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
    git push -fq origin gh-pages > /dev/null

    echo -e "Published Javadoc & Report to gh-pages.\n"
fi
