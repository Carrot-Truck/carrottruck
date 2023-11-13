import { PageContainer, UserInfoSection, ContactInfoSection, SettingsButton, EditableField } from './style';
import { useState, useEffect, useRef } from 'react'
import BackSpace from 'components/atoms/BackHome';
import { info, edit } from 'api/member/client';
import { useNavigate } from 'react-router-dom';
import { AxiosError, AxiosResponse } from 'axios';
import Navbar from "components/organisms/Navbar";

function MyPage() {

    const navigate = useNavigate();

    const editFieldRef = useRef<HTMLInputElement>(null);

    const [userData, setUserData] = useState({
        nickname: '',
        name: '',
        phoneNumber: ''
    });

    const [editMode, setEditMode] = useState({
        nickname: false,
        name: false,
        phoneNumber: false
    });

    const toggleEditMode = (field: keyof typeof editMode) => {
        setEditMode(prevState => {
            // 현재 필드가 이미 활성화되어 있으면, 그 필드만 비활성화합니다.
            if (prevState[field]) {
                return { ...prevState };
            }

            // 새 객체를 생성하여 모든 필드를 비활성화합니다.
            const newEditModeState = Object.keys(prevState).reduce((acc, key) => {
                acc[key as keyof typeof editMode] = false;
                return acc;
            }, {} as typeof editMode);

            // 현재 필드를 활성화합니다.
            newEditModeState[field] = true;

            return newEditModeState;
        });
    }

    const handleInputChange = (field: keyof typeof userData, value: string) => {
        setUserData(prevState => ({ ...prevState, [field]: value }));
    };

    const handleSuccess = (response: AxiosResponse) => {
        console.log(response.data);

        const data = {
            nickname: response.data.data.nickname,
            name: response.data.data.name,
            phoneNumber: response.data.data.phoneNumber
        }
        setUserData(data)
    }

    const handleGetFail = (error: AxiosError) => {
        console.error('Error fetching data', error);
        alert('올바르지 않은 접근입니다.\n다시 로그인하세요.');
        navigate('/');
    };

    const handlePutFail = (error: AxiosError) => {
        console.error('Error fetching data', error);
        alert('내 정보 변경에 실패했습니다.');
        navigate('/mypage/detail');
    }

    const handleClickOutside = (event: MouseEvent) => {
        if (editFieldRef.current && !editFieldRef.current.contains(event.target as Node)) {
            // 현재 활성화된 편집 필드의 이름을 찾습니다.
            const activeField = (Object.keys(editMode) as Array<keyof typeof editMode>).find(key => editMode[key]);

            // 클릭된 요소가 현재 활성화된 편집 필드의 입력 요소 외부인 경우에만 처리합니다.
            if (activeField && editMode[activeField]) {
                // 백엔드로 전체 업데이트된 userData를 전송합니다.
                edit(userData, handleSuccess, handlePutFail);

                // 모든 편집 모드를 종료합니다.
                setEditMode({
                    nickname: false,
                    name: false,
                    phoneNumber: false
                });
            }
        }
    };

    useEffect(() => {
        info(handleSuccess, handleGetFail)
    }, [])

    useEffect(() => {
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [editMode, userData]);

    return (
        <PageContainer>
            <BackSpace></BackSpace>
        <UserInfoSection>
            <div>닉네임</div>
            <EditableField onClick={() => toggleEditMode('nickname')}>
                {editMode.nickname ? (
                    <input
                        ref={editFieldRef}
                        type="text"
                        value={userData.nickname}
                        onChange={(e) => handleInputChange('nickname', e.target.value)}
                        onBlur={() => toggleEditMode('nickname')}
                    />
                ) : (
                    <span>{userData.nickname}</span>
                )}
            </EditableField>
        </UserInfoSection>
        <ContactInfoSection>
            <div>이름</div>
            <EditableField onClick={() => toggleEditMode('name')}>
                {editMode.name ? (
                    <input
                        ref={editFieldRef}
                        type="text"
                        value={userData.name}
                        onChange={(e) => handleInputChange('name', e.target.value)}
                        onBlur={() => toggleEditMode('name')}
                    />
                ) : (
                    <span>{userData.name}</span>
                )}
            </EditableField>
        </ContactInfoSection>
        <ContactInfoSection>
            <div>전화번호</div>
            <EditableField onClick={() => toggleEditMode('phoneNumber')}>
                {editMode.phoneNumber ? (
                    <input
                        ref={editFieldRef}
                        type="text"
                        value={userData.phoneNumber}
                        onChange={(e) => handleInputChange('phoneNumber', e.target.value)}
                        onBlur={() => toggleEditMode('phoneNumber')}
                    />
                ) : (
                    <span>{userData.phoneNumber}</span>
                )}
            </EditableField>
        </ContactInfoSection>
            <SettingsButton>연동된 소셜계정</SettingsButton>
            <Navbar></Navbar>
        </PageContainer>
        
    );
};

export default MyPage;