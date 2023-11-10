import { SelectAddressContainer } from "./style";
import { useEffect, useState } from "react";
import { getDong, getSigungu, getSido } from "api/address";
import Loading from "components/atoms/Loading";
import { AxiosResponse } from "axios";
import ButtonOutline from "components/atoms/ButtonOutline";

interface ISelectAddressProps {
  sidoId: number | null;
  setSidoId: React.Dispatch<React.SetStateAction<number | null>>;
  sigunguId: number | null;
  setSigunguId: React.Dispatch<React.SetStateAction<number | null>>;
  dongId: number | null;
  setDongId: React.Dispatch<React.SetStateAction<number | null>>;
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
}: ISelectAddressProps) {
  const [loading, setLoading] = useState<boolean>(true);
  const [addressNames, setAddressNames] = useState<AddressItem[]>([]);

  useEffect(() => {
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
    fetchData();
  }, []);

  useEffect(() => {
    setLoading(true);
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
    fetchData();
  }, [sidoId, sigunguId]);

  return (
    <SelectAddressContainer>
      {loading ? (
        <Loading />
      ) : (
        addressNames.map((data: AddressItem) => (
          <ButtonOutline
            key={data.id}
            size="s"
            radius="s"
            color="Normal"
            text={data.name}
            handleClick={() => {
              if (!sidoId) {
                setSidoId(data.id);
              } else if (!sigunguId) {
                setSigunguId(data.id);
              } else if (!dongId) {
                setDongId(data.id);
              }
            }}
          />
        ))
      )}
    </SelectAddressContainer>
  );
}

export default SelectAddress;
