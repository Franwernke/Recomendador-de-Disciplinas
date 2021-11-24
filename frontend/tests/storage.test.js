/* eslint-disable no-undef */
import { getDataFromStorage, saveDataInStorage } from '../src/services/Storage';

describe(saveDataInStorage, () => {
  describe('saveDataInStorage', () => {
    const backendData = {
      allDepartments: [{ code: 'MAC' }],
      allDisciplines: [{ code: 'MAC0444' }],
      allCoursesCode: ['45052'],
    };
    let storageMock;

    beforeAll(() => {
      storageMock = {
        setItem: jest.fn(),
        clear: jest.fn(),
      };
    });
    it('should return errors when info is not valid', () => {
      const userData = {
        name: '',
        courseCode: '',
        disciplines: [],
        departments: ['MAC - Departamento de Ciência da Computação'],
        keywords: [],
      };
      const payload = {
        userData,
        backendData,
      };

      const errors = saveDataInStorage(storageMock, payload);

      expect(errors.length).toBe(3);
    });

    it('should not call setItem from storage when info is not valid', () => {
      const userData = {
        name: '',
        courseCode: '',
        disciplines: [],
        departments: ['MAC - Departamento de Ciência da Computação'],
        keywords: [],
      };
      const payload = {
        userData,
        backendData,
      };

      saveDataInStorage(storageMock, payload);

      expect(storageMock.setItem).not.toHaveBeenCalled();
      expect(storageMock.clear).not.toHaveBeenCalled();
    });

    it('should save in the storage when info is valid', () => {
      const userData = {
        name: 'Erick',
        courseCode: '45052',
        disciplines: ['MAC0444'],
        departments: ['MAC - Departamento de Ciência da Computação'],
        keywords: ['Computação'],
      };
      const payload = {
        userData,
        backendData,
      };

      saveDataInStorage(storageMock, payload);

      expect(storageMock.clear).toHaveBeenCalledTimes(1);
      expect(storageMock.setItem).toHaveBeenCalledTimes(5);
    });
  });

  describe('getDataFromStorage', () => {
    const userData = {
      name: 'Foo',
      disciplines: ['MAC0110'],
      departments: ['MAC - Departamento de Ciência da Computação'],
      keywords: ['Computação'],
      courseCode: '45052',
    };
    let storageMock;

    beforeAll(() => {
      storageMock = {
        getItem: jest.fn((key) => JSON.stringify(userData[key])),
      };
    });
    it('should return all saved data', () => {
      const data = getDataFromStorage(storageMock);
      expect(data).toEqual(expect.objectContaining(userData));
    });

    it('should work even though data does not exist', () => {
      const expectData = {
        name: '',
        disciplines: [],
        departments: [],
        keywords: [],
        courseCode: '',
      };
      storageMock.getItem.mockReturnValue(null);
      const data = getDataFromStorage(storageMock);
      expect(data).toEqual(expect.objectContaining(expectData));
    });
  });
});
