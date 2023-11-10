import { SelectAddressContainer } from "./style";
import { useEffect, useState } from "react";
import { getDong, getSigungu, getSido } from "api/address";
import Loading from "components/atoms/Loading";
import { AxiosResponse } from "axios";
import ButtonOutline from "components/atoms/ButtonOutline";
import UnselectAddress from "components/atoms/UnselectAddress";

interface ISelectAddressProps {
  sidoId: number | null;
  setSidoId: React.Dispatch<React.SetStateAction<number | null>>;
  sigunguId: number | null;
  setSigunguId: React.Dispatch<React.SetStateAction<number | null>>;
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
  }, [sidoId, sigunguId]);

  return (
    <SelectAddressContainer>
      {sidoId && (
        <UnselectAddress
          sidoId={sidoId}
          setSidoId={setSidoId}
          sigunguId={sigunguId}
          setSigunguId={setSigunguId}
          setSidoName={setSidoName}
          setSigunguName={setSigunguName}
        />
      )}
      {loading ? (
        <Loading />
      ) : (
        addressNames.map((data: AddressItem) => (
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
                setDongName(data.name);
              }
            }}
          />
        ))
      )}
    </SelectAddressContainer>
  );
}

export default SelectAddress;
