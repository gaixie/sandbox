#!/bin/bash
#
# DISCRIPTION: 通过 travis-ci 自动生成 javadoc 并发布到 github pages.
# see http://benlimmer.com/2013/12/26/automatically-publish-javadoc-to-gh-pages-with-travis-ci/ for details

if [ "$TRAVIS_REPO_SLUG" == "gaixie/sandbox" ] && \
   [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ] && \
   [ "$TRAVIS_PULL_REQUEST" == "false" ] && \
   [ "$TRAVIS_BRANCH" == "develop" ]; then

  echo -e "Publishing javadoc...\n"

  cp -R build/docs/javadoc $HOME/javadoc-snapshot

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/gaixie/sandbox gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./core-api/snapshot/javadoc
  cp -Rf $HOME/javadoc-snapshot ./core-api/snapshot/javadoc
  git add -f .
  git commit -m "Lastest snapshot javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published Javadoc to gh-pages.\n"
  
fi
