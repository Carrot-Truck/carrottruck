import { SelectAddressContainer } from "./style";
import { useEffect, useState } from "react";
import { getDong, getSigungu, getSido } from "api/address";
import Loading from "components/atoms/Loading";
import { AxiosResponse } from "axios";
import ButtonOutline from "components/atoms/ButtonOutline";
import TitleText from "components/atoms/TitleText";

interface ISelectAddressProps {
  sidoId: number | null;
  setSidoId: React.Dispatch<React.SetStateAction<number | null>>;
  sigunguId: number | null;
  setSigunguId: React.Dispatch<React.SetStateAction<number | null>>;
  dongId: number | null;
  setDongId: React.Dispatch<React.SetStateAction<number | null>>;
  setSidoName: React.Dispatch<React.SetStateAction<string>>;
  setSigunguName: React.Dispatch<React.SetStateAction<string>>;
  setDongName: React.Dispatch<React.SetStateAction<string>>;
}

interface AddressItem {
  id: number;
  name: string;
}

const getData = (response: AxiosResponse) => {
  return response.data.data;
};

function SelectAddress({
  sidoId,
  setSidoId,
  sigunguId,
  setSigunguId,
  dongId,
  setDongId,
  setSidoName,
  setSigunguName,
  setDongName,
}: ISelectAddressProps) {
  const [loading, setLoading] = useState<boolean>(true);
  const [addressNames, setAddressNames] = useState<AddressItem[]>([]);

  const fetchData = async () => {
    if (sidoId == null) {
      getSido(
        (response: AxiosResponse) => {
          const data = getData(response);
          setAddressNames(data.address);
        },
        (error: any) => {
          console.log(error);
        }
      );
    } else if (sigunguId == null) {
      getSigungu(
        sidoId,
        (response: AxiosResponse) => {
          const data = getData(response);
          setAddressNames(data.address);
        },
        (error: any) => {
          console.log(error);
        }
      );
    } else {
      getDong(
        sigunguId,
        (response: AxiosResponse) => {
          const data = getData(response);
          setAddressNames(data.address);
        },
        (error: any) => {
          console.log(error);
        }
      );
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    setAddressNames([]);
    setLoading(true);
    fetchData();
  }, [sidoId, sigunguId, dongId]);

  return (
    <SelectAddressContainer>
      {loading ? (
        <Loading />
      ) : (
        <>
          <TitleText text="지역선택" size="m" />
          {addressNames.map((data: AddressItem) => (
            <ButtonOutline
              key={data.id}
              size="s"
              radius="s"
              color="Primary"
              text={data.name}
              handleClick={() => {
                if (!sidoId) {
                  setSidoId(data.id);
                  setSidoName(data.name);
                } else if (!sigunguId) {
                  setSigunguId(data.id);
                  setSigunguName(data.name);
                } else {
                  setDongId(data.id);
                  setDongName(data.name);
                }
              }}
            />
          ))}
        </>
      )}
    </SelectAddressContainer>
  );
}

export default SelectAddress;
