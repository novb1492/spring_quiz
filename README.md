# spring_quiz

spring 레거시에서 
간단한 회원가입/게시판 백엔드 
구현작업을 했습니다

프론트 템플릿은 학원에서 제공해주신거고
백엔드를 만드는 과제였습니다

사용에디터 : ckeditor5  
클라우드 : S3  

summernote/ckeditor5  
둘다 연동할 수 있지만  
summernote는 부트스트랩/j쿼리가 필수지만  
이미지업로드기능이 더 쉽다고생각합니다  
ckediotr5는 사진업로드기능은 더어렵지만  
혼자 붙힐수있고 디자인도 더깔끔해서 선호하는편입니다  

multiparthttpservletrequest 에서 고생을 했습니다  
1.부트에서는 기본제공 기능으로 사용중인데
레거시는 디펜시브+xml을 해줘야하고   
2.boot starter validation을 붙혔다가 
이유는 모르겠지만 validation이 요청을 가로채서
비워버리는 바람에 원인파악이 뭔지 한참찾았습니다

