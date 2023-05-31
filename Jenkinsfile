#!groovy
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

    environment {
        // Define saucelabs url
        SAUCELABS_URL = 'https://ondemand.us-west-1.saucelabs.com:443/wd/hub'
        // Define build time
        BUILD_TIME = new Date().getTime().toString()
        // Define test result for summary message
        TEST_RESULT = ''
    }

    stages {
        stage('mobile testing') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'ngannguyen_saucelab', usernameVariable: 'SAUCELABS_USR', passwordVariable: 'SAUCELABS_ACCESSKEY')]) {
                        container('maven') {
                            // Install maven packages and run tests
                            sh 'mvn clean test -DsuiteFile=src/test/resources/Parallel.xml -Dsaucelab_username=${SAUCELABS_USR} -Dsaucelab_accessKey=${SAUCELABS_ACCESSKEY} -Dsaucelab_URL=${SAUCELABS_URL} -Dbuild=${BUILD_TIME} > build_result.txt || true'
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
                environment {
                    // Get test result from build_result.txt
                    TEST_RESULT = sh (script: 'sed -n -e \'/Results/,/Tests run/ p\' build_result.txt', returnStdout: true).trim()
                    TEST_URL = env.BUILD_URL+'console'
                    ALLURE_REPORT_URL = env.BUILD_URL+'allure-report'
                    SAUCELABS_TEST = "https://app.saucelabs.com/dashboard/tests?environment=vdc&ownerId=0ba44838322e4a288c341e83a377ce9d&ownerType=user&buildVdc=${BUILD_TIME}"
                    
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
                            "text": "```${TEST_RESULT}```"
                            ]
                        ],
                        [
                            "type": "divider"
                        ],
                        [
                            "type": "section",
                            "text": [
                                "type": "mrkdwn",
                                "text": ":pushpin: More info at:\n• *Build URL:* ${TEST_URL}\n• *Allure Report:* ${ALLURE_REPORT_URL}\n • *SauceLabs test:* ${SAUCELABS_TEST}"
                            ]
                        ],
                    ]
                }
                // Send notification
                slackSend channel: 'automation-test-notifications', blocks: blocks, teamDomain: 'agileops', tokenCredentialId: 'jenkins-slack', botUser: true
            }
        }
    }
}
