# import requests
# import json

# # 요청할 URL과 헤더 설정
# url = "https://seungwonlee.cognitiveservices.azure.com/language/:query-knowledgebases?projectName=AIHelpCenter&api-version=2021-10-01&deploymentName=production"
# headers = {
#     "Ocp-Apim-Subscription-Key": "1994b4af82ea4a39bc08fb1b23408c94",
#     "Content-Type": "application/json"
# }

# # 예시 질문 및 설정된 값을 포함한 요청 본문(body)
# data = {
#     "top": 3,
#     "question": "아이디",
#     "includeUnstructuredSources": True,
#     "confidenceScoreThreshold": 0.5,
#     "answerSpanRequest": {
#         "enable": True,
#         "topAnswersWithSpan": 1,
#         "confidenceScoreThreshold": 0.5
#     },
#     # "filters": {
#     #     "metadataFilter": {
#     #         "logicalOperation": "AND",
#     #         "metadata": [
#     #             {"key": "category", "value": "security"}
#     #         ]
#     #     }
#     # }
# }

# # requests 라이브러리를 사용하여 POST 요청을 보냄
# response = requests.post(url, headers=headers, data=json.dumps(data))

# # 응답 출력
# print(response.text)

import streamlit as st
import requests
import json

def query_knowledgebase(question):
    # 요청할 URL과 헤더 설정
    url = "https://seungwonlee.cognitiveservices.azure.com/language/:query-knowledgebases?projectName=AIHelpCenter&api-version=2021-10-01&deploymentName=production"
    headers = {
        "Ocp-Apim-Subscription-Key": "1994b4af82ea4a39bc08fb1b23408c94",
        "Content-Type": "application/json"
    }

    # 요청 본문(body) 설정
    data = {
        "top": 3,
        "question": question,
        "includeUnstructuredSources": True,
        "confidenceScoreThreshold": 0.7,
        "answerSpanRequest": {
            "enable": True,
            "topAnswersWithSpan": 1,
            "confidenceScoreThreshold": 0.2
        }
    }

    # requests 라이브러리를 사용하여 POST 요청을 보냄
    response = requests.post(url, headers=headers, data=json.dumps(data))
    
    # 응답을 JSON 형태로 변환하여 반환
    return response.json()

def main():
    st.title("Knowledgebase Query App")

    # 사용자로부터 질문 입력 받기
    question = st.text_input("Enter your question:", "")

    # "Submit" 버튼
    submit = st.button("Submit")

    if submit and question:
        with st.spinner('Fetching answers...'):
            response = query_knowledgebase(question)
            print(response)
            
            # 결과 출력, 첫 번째 답변의 'answer' 값만 추출하여 표시
            if 'answers' in response and len(response['answers']) > 0:
                answer = response['answers'][0]['answer']
                st.write("Response:")
                st.write(answer)  # 답변 내용을 텍스트로 출력
            else:
                st.write("No answers found.")

if __name__ == "__main__":
    main()
