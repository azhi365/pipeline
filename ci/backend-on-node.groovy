node {

    scm         = env.scm
    target_host = env.target_host
    target_dir  = env.target_dir
    target_port = env.target_port
    app_name    = env.app_name
    stage('Clone') {
        echo '1.clone code'
        git changelog: true, branch: "master", credentialsId: 'gitee', url: scm
    }

    stage('Build') {
        echo '2.build project'
        sh "mvn -Dmaven.test.skip=true clean package"
    }

    stage('Test') {
        echo '3.code test'
    }

    stage('Deploy') {
        echo '4.deploy project'
        sh "scp -P ${env.target_port} target/${app_name} root@${target_host}:${target_dir}${app_name}"
        sh "ssh -p ${env.target_port} root@${target_host} \"${target_dir}run.sh stop\""
        sh "ssh -p ${env.target_port} root@${target_host} \"${target_dir}run.sh start\""
    }

}
