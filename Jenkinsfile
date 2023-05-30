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
              - name: maven
                image: maven:3.8.6-openjdk-11-slim
                command:
                - cat
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
        stage('mobile testing') {
            environment {
                SAUCELABS_DIR = "${WORKSPACE}/src/test/resources/Parallel.xml"
                SAUCELABS_URL = 'https://ondemand.us-west-1.saucelabs.com:443/wd/hub'
            }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'ngannguyen_saucelab', passwordVariable: 'SAUCELABS_USR', usernameVariable: 'SAUCELABS_PWD')]) {
                        container('maven') {
                            // Install maven packages and run tests
                            sh 'mvn clean test -DsuiteXmlFile=${SAUCELABS_DIR} -Dsaucelabs.url=${SAUCELABS_URL} -Dsaucelabs.username=${SAUCELABS_USR} -Dsaucelabs.accessKey=${SAUCELABS_PWD} || true'
                        }
                    }
                }
            }
        }

        stage('report generation'){
            steps {
                script {
                    container('allure') {
                        // Generate Allure report
                        sh 'allure generate --clean'
                    }
                }
            }
        }
    }

    post {
        always {
            // Archive test results
            archiveArtifacts artifacts: 'allure-results/**/*'
            // Publish test report for easy viewing
            publishHTML (target : [allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'allure-report',
            reportFiles: 'index.html',
            reportName: 'allure-report',
            reportTitles: '', 
            useWrapperFileDirectly: true])

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
