import styled from 'styled-components'

export const MyPagelayout = styled.div`
  width: 360px;
  padding-bottom: 200px;
`;

export const PageContainer = styled.div`
  display: flex;
  color: black;
  flex-direction: column;
  padding: 20px;
`;

export const UserInfoSection = styled.section`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 10px;
  // Add more styles as needed
`;

export const ContactInfoSection = styled(UserInfoSection)`
  // Inherits styles from UserInfoSection and you can add or override styles
`;

export const SettingsButton = styled.button`
  background-color: green;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  margin-top: 20px;
  cursor: pointer;
  // Add more styles as needed
`;

export const EditableField = styled.div`
  cursor: pointer;
  input {

  }
  span {

  }
`;