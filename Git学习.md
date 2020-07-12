# Git命令学习

## Git基本命令

- 初始化本地仓库

  ```shell
  git clone ${remote_url} #从远程仓库克隆
  git init ${repository_name} #初始化本地目录
  ```

- 从远程分支拉取文件

  ```bash
  git pull
  ```

  

- 将文件添加至暂存区

  ```shell
  git add ${filename} #将一个文件添加至暂存区
  git add . #将增、改文件提交至暂存区（不包括删）
  git add -u #(--update)将删、改文件提交至暂存区（不包括增）
  git add -A #(--all)提交所有修改（增、删、改） 
  ```

- 将文件移出暂存区

  ```shell
  git rm --cached ${filename} 
  ```

  

- 将文件提交至本地仓库

  ```shell
  git commit ${filename} -m ${msg} #提交某一文件
  git commit -am ${msg} #提交暂存区所有文件
  ```

  

- 将本地分支推送至远程分支

  ```shell
  git push
  ```

  

- 快速添加、提交、推送

  ```shell
  git add -A; git commit -am ${msg}; git push
  ```

- 查看提交历史

  ```bash
  git log
  git log --oneline #每次提交在一行显示
  git log --graph #显示合并图
  ```

## Git分支管理

- 查看分支

  ```shell
  git branch #查看当前分支
  git branch -a #查看所有分支
  git branch -avv #查看所有分支详细信息
  ```

- 切换分支

  ```shell
  git checkout ${branch_name}
  ```

  

- 新建分支

  ```shell
  git branch ${branch_name} #基于当前分支创建新分支
  git checkout -b ${branch_name} #创建并切换至分支
  #相当于git branch ${branch_name}; git checkout ${branch_name}
  ```

- 删除分支

  ```shell
  #删除本地分支
  git branch -d ${branch_name} #--delete 该分支必须完全和它的上游分支merge完成
  git branch -D ${branch_name} #--dalete --force 无需merge删除
  #删除远程分支
  git push origin --delete -d ${branch_name} #该指令也会删除追踪分支
  #如果从网页删除远程分支，追踪分支是不会删除的。,可以通过git fetch origin -p(--prune) branch_name来单独删除追踪分支
  ```

- 合并分支

  ```shell
  git merge ${target_branch} #将目标分支合并至当前分支
  ```

- 冲突解决

  ```bash
  git merge ${sourcebranch}	(Merge conflict appears)
  git status #view conflict details & fix conflicts
  git commit -m ${msg} #(add then commit)
  git push
  ```

   