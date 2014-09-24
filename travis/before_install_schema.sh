#!/bin/bash
#
# SCRIPT:  
#     ./travis/before_install_schema.sh
# PURPOSE: 
#     通过 git 远程 clone schema 项目，并 switch 到相应分支
# DISCRIPTION: 
#     在使用 travis-ci 集成时，要先设置初始化数据库 schema，所以要先 clone schema 项目。
#     注意集成时代码库的 master 和 develop 分支也要和 jibu-schema 项目的分支对应。

cd $HOME
git clone --quiet git@github.com:gaixie/jibu-schema.git

# 如果当前集成的分支是 develop，将 jibu-schema 项目的分支也 checkout 到 develop 上。
if [[ $TRAVIS_BRANCH == 'develop' ]]
then
  cd jibu-schema/
  git checkout --track origin/develop
fi
