pipeline {
    agent {
        kubernetes {
        yaml '''
            apiVersion: v1
            kind: Pod
            metadata:
              labels:
                jenkin-job: appium
            spec:
              imagePullSecrets:
              - name: regcred
              containers:
              - name: appium
                image: appium/appium:v2.0.b63-p2
                command: ["/bin/sh", "-c", "sleep 3000"]
                env:
                - name: JENKINS_NODE_COOKIE
                  value: "dontKillMe"
                volumeMounts:
                - name: shared-data
                  mountPath: /data
              - name: maven
                image: maven:3.8.6-openjdk-11-slim
                command: ["/bin/sh", "-c", "sleep 3000"]
                tty: true
                volumeMounts:
                - name: shared-data
                  mountPath: /data
              - name: allure
                image: frankescobar/allure-docker-service:2.19.0
                command:
                - cat
                tty: true
                volumeMounts:
                - name: shared-data
                  mountPath: /data
              volumes:
              - name: shared-data
                emptyDir: {}
            '''
        }
    }

    stages {
        stage('start appium server') {
            steps {
                script {
                    container('appium') {
                        try {
                            sh 'appium'
                        } catch (err) {
                            echo "Cannot start appium server"
                        }
                    }
                }
            }
        }

        // stage('mobile testing') {
        //     environment {
        //         SAUCELABS_DIR = "${WORKSPACE}/src/test/resources/Parallel.xml"
        //         SAUCELABS_URL = 'https://ondemand.us-west-1.saucelabs.com:443/wd/hub'
        //         SAUCELABS = credentials('ngannguyen_saucelab')
        //     }
        //     steps {
        //         script {
        //             // Install maven packages and run tests
        //             container('maven') {
        //                 try {
        //                     sh """
        //                     mvn clean install
        //                     mvn clean test -DsuiteFile=${SAUCELABS_DIR} -Dsaucelab_username=${SAUCELABS_USR} -Dsaucelab_accessKey=${SAUCELABS_PWD} -Dsaucelab_URL=${SAUCELABS_URL}
        //                     """
        //                 } catch (err) {
        //                     echo "Test failed"
        //                 }
        //             }
        //         }
        //     }
        // }


    //     stage('publish report'){
    //         steps {
    //             script {
    //                 container('allure') {
    //                     try {
    //                         sh 'allure generate --clean'
    //                     } catch (err) {
    //                         echo "Cannot generate allure report"
    //                     }
    //                 }
    //             }
    //         }
    //     }
    }

    post {
        always {
            // Archive test results
            // archiveArtifacts artifacts: 'allure-results/**/*'
            // Publish test report for easy viewing
            // publishHTML (target : [allowMissing: false,
            // alwaysLinkToLastBuild: true,
            // keepAll: true,
            // reportDir: 'allure-report',
            // reportFiles: 'index.html',
            // reportName: 'allure-report',
            // reportTitles: '', 
            // useWrapperFileDirectly: true])

            script {
                // Define Slack message blocks
                def blocks = [
                    [
                        "type": "header",
                        "text": [
                            "type": "plain_text",
                            "text": "FINISHED TEST",
                        ]
                    ],
                    [
                        "type": "divider"
                    ],
                    [
                        "type": "section",
                        "text": [
                            "type": "mrkdwn",
                            "text": ":sunny: Job *${env.JOB_NAME}*'s result is ${currentBuild.currentResult}.\n*Summary:*"
                        ]
                    ],
                    [
                    "type": "section",
                    "text": [
                        "type": "mrkdwn",
                        // "text": "```${result}```"
                        ]
                    ],
                    [
                        "type": "divider"
                    ],
                    [
                        "type": "section",
                        "text": [
                            "type": "mrkdwn",
                            "text": ":pushpin: More info at:\n• *Build URL:* ${env.BUILD_URL}console\n• *Allure Report:* ${env.BUILD_URL}allure-report"
                        ]
                    ],
                ]
            }

            // Send notification
            // slackSend channel: 'automation-test-notifications', blocks: blocks, teamDomain: 'agileops', tokenCredentialId: 'jenkins-slack', botUser: true
        }
    }
}
