node {
    scm         = env.scm
    target_host = env.target_host
    target_dir  = env.target_dir
    target_port = env.target_port
    app_name = ""
    stage('Clone') {
        echo '1.clone code'
        git changelog: true, branch: "master", credentialsId: 'gitee', url: scm
    }

    stage('Build') {
        echo '2.build project'
        sh "pwd"
        sh "npm install"
        sh "npm run build"
    }

    stage('Test') {
        echo '3.code test'
    }

    stage('Deploy') {
        echo '4.deploy project'
        sh "scp -P ${target_port} -r dist/* root@${target_host}:${target_dir}"
    }
}
